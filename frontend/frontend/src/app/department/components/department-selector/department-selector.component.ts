import { Component, OnInit } from '@angular/core';
import {DepartmentService} from "../../department.service";
import {DepartmentDto} from "../../../typings";

@Component({
  selector: 'department-selector',
  templateUrl: './department-selector.component.html',
  styleUrls: ['./department-selector.component.css']
})
export class DepartmentSelectorComponent implements OnInit {
  public departments: DepartmentDto[] = [];

  constructor(private departmentService: DepartmentService) { }

  public ngOnInit(): void {
    this.departmentService.getAllDepartments().subscribe((departments: DepartmentDto[]) => {
      this.departments = departments;
    });
  }

}
