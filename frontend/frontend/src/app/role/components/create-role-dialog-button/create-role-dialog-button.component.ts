import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {CreateRoleComponent} from "../create-role/create-role.component";
import {AccessRightDto} from "../../../typings";

@Component({
  selector: 'create-role-dialog-button',
  templateUrl: './create-role-dialog-button.component.html',
  styleUrls: ['./create-role-dialog-button.component.css']
})
export class CreateRoleDialogButtonComponent implements OnInit {

  @Input() public accessRights: AccessRightDto[] = [];
  @Output() createNewRoleEvent = new EventEmitter();

  constructor(private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.dialog.afterAllClosed.subscribe(() => {
      this.createNewRoleEvent.emit();
    });
  }

  public openDialog(): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {accessRights: this.accessRights};
    this.dialog.open(CreateRoleComponent, dialogConfig);
  }
}
