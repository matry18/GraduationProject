import { Component, OnInit } from '@angular/core';
import {DepartmentDto} from "../../../typings";
import {DepartmentService} from "../../department.service";

@Component({
  selector: 'department-list',
  templateUrl: './department-list.component.html',
  styleUrls: ['./department-list.component.css']
})
export class DepartmentListComponent implements OnInit {

  public departmentList: DepartmentDto[] = [];

  displayedColumns = ['departmentName'];

  constructor(private departmentService: DepartmentService) { }

  ngOnInit(): void {
    this.getAllDepartments();
  }

  private getAllDepartments(): void {
    this.departmentService.getAllDepartments().subscribe((departmentList: DepartmentDto[]) => {
      this.departmentList = departmentList;
    })
  }

}
