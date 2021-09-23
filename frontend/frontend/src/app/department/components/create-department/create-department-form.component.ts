import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";
import {SnackbarService} from "../../../snackbars/FormSubmission/snackbar.service";
import {Subscription} from "rxjs";
import {CitizenDto, DepartmentDto} from "../../../typings";
import {DepartmentService} from "../../department.service";

@Component({
  selector: 'create-department-form',
  templateUrl: './create-department-form.component.html',
  styleUrls: ['./create-department-form.component.css']
})
export class CreateDepartmentFormComponent implements OnInit {

  public departmentForm: FormGroup;

  constructor(private fb: FormBuilder,
              private dialogRef: MatDialogRef<CreateDepartmentFormComponent>,
              private snackbarService: SnackbarService,
              private departmentService: DepartmentService) {
    this.departmentForm = fb.group({
      departmentName: ["", Validators.required],
    })

  }

  ngOnInit(): void {
  }

  public submit(): void {
    let subscription: Subscription = this.departmentService.createDepartment(this.departmentForm.value)
      .subscribe(
        (departmentDto: DepartmentDto) => this.snackbarService.openSuccessfulSnackBar(),
        () => this.snackbarService.openFailedSnackBar()
      );
  }

  public cancel(): void {
    this.dialogRef.close();
  }
}
