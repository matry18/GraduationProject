import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'create-employee-form',
  templateUrl: './create-employee-form.component.html',
  styleUrls: ['./create-employee-form.component.css']
})
export class CreateEmployeeFormComponent implements OnInit {
  public employeeForm: FormGroup;

  constructor(private fb: FormBuilder, public dialogRef: MatDialogRef<CreateEmployeeFormComponent>) {
    this.employeeForm = this.fb.group({
      firstname: ["", Validators.required],
      lastname: ["", Validators.required],
      username: ["", Validators.required],
      password: ["", Validators.required],
    })
  }

  ngOnInit(): void {
  }

  public submit(): void {

  }

  public cancel(): void {
    this.dialogRef.close();
  }
}
