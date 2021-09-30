import { Component, OnInit } from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {ResidentCreateFormComponent} from "../resident-create-form/resident-create-form.component";

@Component({
  selector: 'create-citizen-dialog',
  templateUrl: './resident-create-dialog.component.html',
  styleUrls: ['./resident-create-dialog.component.css']
})
export class ResidentCreateDialogComponent implements OnInit {

  constructor(private dialog: MatDialog) { }

  ngOnInit(): void {
  }
  openDialog() {
    this.dialog.open(ResidentCreateFormComponent);
  }
}
