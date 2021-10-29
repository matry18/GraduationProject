import {Component, Inject, OnInit, Output, EventEmitter} from '@angular/core';
import {RoleService} from "../../role.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AccessRightDto} from "../../../typings";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {SnackbarService} from "../../../snackbars/FormSubmission/snackbar.service";


@Component({
  selector: 'create-role',
  templateUrl: './create-role.component.html',
  styleUrls: ['./create-role.component.css']
})
export class CreateRoleComponent implements OnInit {


  public roleForm: FormGroup;
  public accessRights: AccessRightDto[] = [];
  public selectedAccessRights: AccessRightDto[] = [];


  constructor(private roleService: RoleService,
              private fb: FormBuilder,
              private dialogRef: MatDialogRef<CreateRoleComponent>,
              @Inject(MAT_DIALOG_DATA) data: any,
              private snackbarService: SnackbarService) {

    this.roleForm = this.fb.group({
      name: ['', Validators.required],
    });

    this.accessRights = data.accessRights;
  }

  public ngOnInit(): void {
  }

  private addToSelectedAccessRights(accessRightDto: AccessRightDto): void {
    this.selectedAccessRights.push(
      accessRightDto
    );
  }

  public addOrRemoveFromSelectedArray(accessRightDto: AccessRightDto, index: number) {
    if (this.selectedAccessRights.includes(accessRightDto)) {
      this.removedFromSelectedAccessRights(index);
    } else {
      this.addToSelectedAccessRights(accessRightDto);
    }
  }

  private removedFromSelectedAccessRights(index: number): void {
    this.selectedAccessRights.splice(index, 1);
  }

  public submit(): void {
    this.roleService.createRole({
      id: '',
      name: this.roleForm.controls['name'].value,
      accessRights: this.selectedAccessRights
    }).subscribe((role) => {
      this.snackbarService.openSuccessfulSnackBar();
    }, () => {
      this.snackbarService.openFailedSnackBar();
    });
  }

  public cancel(): void {
    this.dialogRef.close();
  }

}
