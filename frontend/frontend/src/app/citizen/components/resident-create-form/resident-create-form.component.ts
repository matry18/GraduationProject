import {Component, EventEmitter, Inject, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ResidentService} from "../../resident.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormSubmissionPositiveComponent} from "../../../snackbars/FormSubmission/form-submission-positive/form-submission-positive.component";
import {DepartmentDto, ResidentDto} from "../../../typings";
import {FormSubmissionNegativeComponent} from "../../../snackbars/FormSubmission/form-submission-negative/form-submission-negative.component";
import {Subscription} from "rxjs";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {SnackbarService} from "../../../snackbars/FormSubmission/snackbar.service";
import {DepartmentService} from "../../../department/department.service";

@Component({
  selector: 'create-citizen-form',
  templateUrl: './resident-create-form.component.html',
  styleUrls: ['./resident-create-form.component.css']
})
export class ResidentCreateFormComponent implements OnInit {
  public residentForm: FormGroup;
  public formText: string = '';
  public isEditMode: boolean = false;
  public departments: DepartmentDto[] = [];

  @Output() cancelEvent = new EventEmitter();


  constructor(private fb: FormBuilder, private citizenService: ResidentService,
              public dialogRef: MatDialogRef<ResidentCreateFormComponent>,
              @Inject(MAT_DIALOG_DATA) public data: ResidentDto,
              private snackbarService: SnackbarService,
              private departmentService: DepartmentService) {

    this.isEditMode = !!this.data;
    this.formText = this.isEditMode ? `Edit resident (${data.firstname} ${data.lastname})` : 'Create resident';
    console.log(data);

    if(!this.isEditMode) {
      this.residentForm = this.fb.group(
        {
          id: [data ?  data.id : null],
          firstname: [data ?  data.firstname : "" , Validators.required],
          lastname: [data ? data.lastname : "", Validators.required],
          email: [data ? data.email : "", [Validators.required, Validators.email]],
          phoneNumber: [data ? data.phoneNumber : "", Validators.required],
          department: [data ? data.department.departmentName : "", Validators.required],
          //these should be removed when we get Kafka, Orchestrator, and Authentication services up.
          username: [data ? data.username : "", Validators.required],
          password: [data ? data.password : "", Validators.required],
        }
      );

    } else {
      this.residentForm = this.fb.group(
        {
          firstname: [data ?  data.firstname : "" , Validators.required],
          lastname: [data ? data.lastname : "", Validators.required],
          email: [data ? data.email : "", [Validators.required, Validators.email]],
          phoneNumber: [data ? data.phoneNumber : "", Validators.required],
          department: [data ? data.department.departmentName : "", Validators.required],

        }
      );
    }




  }

  public ngOnInit(): void {
    this.departmentService.getAllDepartments().subscribe((departments: DepartmentDto[]) => {
      this.departments = departments;
    });

  }

  public submit(): void {
    console.log(this.residentForm.get('department')?.value);
    if(!this.isEditMode) {
      let subscription: Subscription = this.citizenService.createCitizen(this.residentForm.value)
        .subscribe(
          (citizen: ResidentDto) => this.snackbarService.openSuccessfulSnackBar(),
          () => this.snackbarService.openFailedSnackBar()
        );
    } else {
      let subscription: Subscription = this.citizenService.editCitizen(this.residentForm.value)
        .subscribe(
          (citizen: ResidentDto) => this.snackbarService.openSuccessfulSnackBar(),
          () => this.snackbarService.openFailedSnackBar()
        );
    }

  }

  public cancel() {
    this.dialogRef.close();
  }
}
