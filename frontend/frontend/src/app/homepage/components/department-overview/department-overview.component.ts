import { Component, OnInit } from '@angular/core';
import {DepartmentService} from "../../../department/department.service";

@Component({
  selector: 'department-overview',
  templateUrl: './department-overview.component.html',
  styleUrls: ['./department-overview.component.css']
})
export class DepartmentOverviewComponent implements OnInit {

  public departmentCount: number = 0;
  constructor(private departmentService : DepartmentService) { }

  ngOnInit(): void {
    this.departmentService.getDepartmentCount().subscribe((
      departments: number
      ) => {
      this.departmentCount = departments;
      }
    )
  }

}
