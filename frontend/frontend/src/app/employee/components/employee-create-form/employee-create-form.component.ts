import {Component, EventEmitter, Inject, OnDestroy, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DepartmentDto, EmployeeDto, RoleDto} from "../../../typings";
import {Subscription} from "rxjs";
import {EmployeeService} from "../../employee.service";
import {SnackbarService} from "../../../snackbars/FormSubmission/snackbar.service";
import {DepartmentService} from "../../../department/department.service";
import {RoleService} from "../../../role/role.service";
import {AuthorizationService} from "../../../shared-services/authorization.service";

interface EmployeeData {
  employee: EmployeeDto
}

@Component({
  selector: 'create-employee-form',
  templateUrl: './employee-create-form.component.html',
  styleUrls: ['./employee-create-form.component.css']
})
export class EmployeeCreateFormComponent implements OnInit, OnDestroy {
  public employeeForm: FormGroup;
  public formText: string ='';
  public isEditMode: boolean = false;
  public departments: DepartmentDto[] = [];
  public roles:Array<RoleDto> = new Array<RoleDto>();
  private subscription: Subscription | null = null;

  @Output() cancelEvent = new EventEmitter();

  constructor(private fb: FormBuilder, private employeeService: EmployeeService,
              public dialogRef: MatDialogRef<EmployeeCreateFormComponent>,
              @Inject(MAT_DIALOG_DATA) public data: EmployeeData,
              private snackbarService: SnackbarService,
              private departmentService: DepartmentService,
              private roleService: RoleService,
              private authorizationService: AuthorizationService) {

    this.isEditMode = this.data !== null && this.data !== undefined;
    this.formText = this.isEditMode ? `Edit Employee (${data.employee.firstname} ${data.employee.lastname})` : 'Create Employee';

    if(!this.isEditMode) {
      this.employeeForm = this.fb.group(
        {
          id: [null],
          firstname: ["", Validators.required],
          lastname: ["", Validators.required],
          email: ["", [Validators.required, Validators.email]],
          phoneNumber: ["", Validators.required],
          department: ["", Validators.required],
          roleDto:["", Validators.required],
          //these should be removed when we get Kafka, Orchestrator, and Authentication services up.
          username: ["", Validators.required],
          password: ["", Validators.required],
        }
      );
    } else {
      this.employeeForm = this.fb.group(
        {
          id: [data.employee.id],
          firstname: [data.employee.firstname, Validators.required],
          lastname: [data.employee.lastname, Validators.required],
          email: [data.employee.email, [Validators.required, Validators.email]],
          phoneNumber: [data.employee.phoneNumber, Validators.required],
          department: [null, [Validators.required]],
          roleDto:["", Validators.required]
        }
      );
    }
  }

  ngOnInit(): void {
    this.departmentService.getAllDepartments().subscribe((departments: DepartmentDto[]) => {
      this.departments = departments;
      });

    this.fetchRoles();
    this.employeeForm.get('department')?.setValue(this.data?.employee?.department);
  }

  private fetchRoles(): void {
    this.roleService.getAllRoles().subscribe((roles: RoleDto[]) => {
      this.roles = roles;
      if(!this.authorizationService.hasRole(['admin'])) {
        this.roles = this.roles.filter(role => role.name !== 'admin');
      }
    });
  }

  public submit(): void {
    if(!this.isEditMode) {
      this.subscription = this.employeeService.createEmployee(this.employeeForm.value).subscribe(
        (employee: EmployeeDto) => this.snackbarService.openSuccessfulSnackBar(),
        () => this.snackbarService.openFailedSnackBar()
        );
    } else {
      this.subscription = this.employeeService.editEmployee(this.employeeForm.value)
        .subscribe(
        (employee: EmployeeDto) => this.snackbarService.openSuccessfulSnackBar(),
        () => this.snackbarService.openFailedSnackBar()
      );
    }
  }

  public cancel(): void {
    this.dialogRef.close();
  }

  ngOnDestroy() {
    this.subscription?.unsubscribe();
  }
}
