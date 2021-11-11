import {Component, EventEmitter, Inject, OnDestroy, OnInit, Output} from '@angular/core';
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

interface ResidentData {
  resident: ResidentDto;
}

@Component({
  selector: 'create-citizen-form',
  templateUrl: './resident-create-form.component.html',
  styleUrls: ['./resident-create-form.component.css']
})
export class ResidentCreateFormComponent implements OnInit, OnDestroy {
  public residentForm: FormGroup;
  public formText: string = '';
  public isEditMode: boolean = false;
  public departments: DepartmentDto[] = [];
  private subscription: Subscription | null = null;

  @Output() cancelEvent = new EventEmitter();


  constructor(private fb: FormBuilder, private citizenService: ResidentService,
              public dialogRef: MatDialogRef<ResidentCreateFormComponent>,
              @Inject(MAT_DIALOG_DATA) public data: ResidentData,
              private snackbarService: SnackbarService,
              private departmentService: DepartmentService) {

    this.isEditMode = this.data !== null && this.data !== undefined;
    this.formText = this.isEditMode ? `Edit Resident (${data.resident.firstname} ${data.resident.lastname})` : 'Create Resident';


    if(!this.isEditMode) {
      this.residentForm = this.fb.group(
        {
          id: [null],
          firstname: ["" , Validators.required],
          lastname: ["" ,Validators.required],
          email: ["", [Validators.required, Validators.email]],
          phoneNumber: ["", [Validators.required, Validators.pattern('[- +()0-9]+')]],
          department: ["", Validators.required],
          //these should be removed when we get Kafka, Orchestrator, and Authentication services up.
          username: ["", Validators.required],
          password: ["", Validators.required],
        }
      );

    } else {
      this.residentForm = this.fb.group(
        {
          id: [data.resident.id],
          firstname: [data.resident.firstname, Validators.required],
          lastname: [data.resident.lastname, Validators.required],
          email: [data.resident.email, [Validators.required, Validators.email]],
          phoneNumber: [data.resident.phoneNumber, [Validators.required, Validators.pattern('[- +()0-9]+')]],
          department: [null, [Validators.required]]
        }
      );
    }
  }

  public ngOnInit(): void {
    this.departmentService.getAllDepartments().subscribe((departments: DepartmentDto[]) => {
      this.departments = departments;
    });
    this.residentForm.get('department')?.setValue(this.data?.resident?.department);
  }

  public submit(): void {
    if(!this.isEditMode) {
      this.subscription = this.citizenService.createCitizen(this.residentForm.value)
        .subscribe(
          (resident: ResidentDto) => this.snackbarService.openSuccessfulSnackBar(),
          () => this.snackbarService.openFailedSnackBar()
        );
    } else {
      this.subscription = this.citizenService.editCitizen(this.residentForm.value)
        .subscribe(
          (resident: ResidentDto) => this.snackbarService.openSuccessfulSnackBar(),
          () => this.snackbarService.openFailedSnackBar()
        );
    }
  }

  public cancel() {
    this.dialogRef.close();
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }
}
