import {Component, OnInit} from "@angular/core";
import {EmployeeDto} from "../../../typings";
import {EmployeeService} from "../../../employee/employee.service";
import {MatDialog} from "@angular/material/dialog";
import {SnackbarService} from "../../../snackbars/FormSubmission/snackbar.service";
import {EmployeeCreateFormComponent} from "../../../employee/components/employee-create-form/employee-create-form.component"
import {AuthorizationService} from "../../../shared-services/authorization.service";

@Component({
  selector: 'employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {

  public employeeList: EmployeeDto[] = [];

  displayedColumns = ['id', 'firstname', 'lastname', 'department', 'email', 'phoneNumber'];

  constructor(private enployeeService: EmployeeService,
              private dialog: MatDialog,
              private snackbarService: SnackbarService,
              public authorizationService: AuthorizationService) { }

  ngOnInit(): void {
    this.fetchAllEmployees();
  }

  private fetchAllEmployees(): void {
    this.enployeeService.getAllEmployees().subscribe((employeeList: EmployeeDto[]) => {
      this.employeeList = employeeList;
    });
  }

  public deleteEmployee(employeeId: string): void {
    if(!this.authorizationService.hasAccessRight(['delete','admin'])) {
      return;
    }
    this.enployeeService.deleteEmployee(employeeId).subscribe(
      ()=> {
        this.fetchAllEmployees();
        this.snackbarService.openSuccessfulSnackBar();
      },
      () => {this.snackbarService.openFailedSnackBar();}
    );
  }

  public openEditDialog(employeeDto: EmployeeDto): void {
    if(!this.authorizationService.hasAccessRight(['update','admin'])) {
      return;
    }
    this.dialog.open(EmployeeCreateFormComponent,{
      data: {employee: employeeDto}});
    this.dialog.afterAllClosed.subscribe(() =>
      this.fetchAllEmployees());
  }

}
