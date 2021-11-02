import { Component, OnInit } from '@angular/core';
import {EmployeeService} from "../../../employee/employee.service";

@Component({
  selector: 'employee-backedup',
  templateUrl: './employee-backedup.component.html',
  styleUrls: ['./employee-backedup.component.css']
})
export class EmployeeBackedupComponent implements OnInit {

  public backedupEmployeeCount : number = 0;
  constructor(private employeeService: EmployeeService) { }

  ngOnInit(): void {
    this.employeeService.getBackedupEmployeesCount().subscribe((
      backedupEmployees: number
    ) => {
      this.backedupEmployeeCount = backedupEmployees;
    })
  }

}
