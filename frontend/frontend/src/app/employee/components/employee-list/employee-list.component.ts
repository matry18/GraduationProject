import {Component, OnInit} from "@angular/core";
import {EmployeeDto} from "../../../typings";
import {EmployeeService} from "../../../employee/employee.service";
import {MatDialog} from "@angular/material/dialog";
import {SnackbarService} from "../../../snackbars/FormSubmission/snackbar.service";
//import {ResidentCreateFormComponent} from "../../../citizen/components/resident-create-form/resident-create-form.component";
import {EmployeeCreateFormComponent} from "../../../employee/components/employee-create-form/employee-create-form.component"


@Component({
  selector: 'employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {

  public employeeList: EmployeeDto[] = [];

  displayedColumns = ['id', 'firstname', 'lastname', 'department', 'email', 'phoneNumber'];

  constructor(private enployeeService: EmployeeService, private dialog: MatDialog, private snackbarService: SnackbarService) { }

  ngOnInit(): void {
    this.fetchAllEmployees();
  }

  private fetchAllEmployees(): void {
    this.enployeeService.getAllEmployees().subscribe((employeeList: EmployeeDto[]) => {
      this.employeeList = employeeList;
    });

  }

  public deleteEmployee(employeeId: string): void {
    this.enployeeService.deleteEmployee(employeeId).subscribe(
      ()=> {
        this.fetchAllEmployees();
        this.snackbarService.openSuccessfulSnackBar();
      },
      () => {this.snackbarService.openFailedSnackBar();}
    );
  }

  public openEditDialog(employeeDto: EmployeeDto): void {
    this.dialog.open(EmployeeCreateFormComponent,{
      data: {employee: employeeDto}});
    this.dialog.afterAllClosed.subscribe(() =>
      this.fetchAllEmployees());
  }


}
