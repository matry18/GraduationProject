import { Component, OnInit } from '@angular/core';
import {EmployeeService} from "../../../employee/employee.service";

@Component({
  selector: 'employee-overview',
  templateUrl: './employee-overview.component.html',
  styleUrls: ['./employee-overview.component.css']
})
export class EmployeeOverviewComponent implements OnInit {

  public employeeCount: number = 0;
  constructor(private employeeService : EmployeeService) { }

  ngOnInit(): void {
    this.employeeService.getEmployeeCount().subscribe((
      employees: number
      ) => {
      this.employeeCount = employees;
      }
    )
  }

}
