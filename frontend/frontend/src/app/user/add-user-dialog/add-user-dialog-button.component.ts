import { Component, OnInit } from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {AddUserMenuComponent} from "../add-user-menu/add-user-menu.component";

@Component({
  selector: 'add-user-dialog-button',
  templateUrl: './add-user-dialog-button.component.html',
  styleUrls: ['./add-user-dialog-button.component.css']
})
export class AddUserDialogButtonComponent implements OnInit {

  constructor(private dialog: MatDialog) { }

  ngOnInit(): void {
  }


  public openDialog(): void {
    this.dialog.open(AddUserMenuComponent);
  }
}
