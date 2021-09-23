import { Component, OnInit } from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {AddUserMenuComponent} from "../../../user/add-user-menu/add-user-menu.component";
import {CreateDepartmentFormComponent} from "../create-department/create-department-form.component";

@Component({
  selector: 'create-department-dialog-button',
  templateUrl: './create-department-dialog-button.component.html',
  styleUrls: ['./create-department-dialog-button.component.css']
})
export class CreateDepartmentDialogButtonComponent implements OnInit {

  constructor(private dialog: MatDialog) { }

  ngOnInit(): void {
  }

  public openDialog(): void {
    this.dialog.open(CreateDepartmentFormComponent);
  }
}
