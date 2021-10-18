import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeCreateFormComponent} from "./components/employee-create-form/employee-create-form.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { MatButtonModule} from "@angular/material/button";
import { MatCardModule} from "@angular/material/card";
import { EmployeeListComponent} from "./components/employee-list/employee-list.component";
import { MatFormFieldModule} from "@angular/material/form-field";
import {MatTableModule} from "@angular/material/table";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatSelectModule} from "@angular/material/select";
import {MatDialogModule} from "@angular/material/dialog";

@NgModule({
  declarations: [
    EmployeeCreateFormComponent,
    EmployeeListComponent
  ],
  exports: [
    EmployeeListComponent,
    EmployeeCreateFormComponent
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatTableModule,
    MatInputModule,
    MatIconModule,
    CommonModule,
    MatTooltipModule,
    MatSelectModule,
    MatDialogModule
  ]
})
export class EmployeeModule { }
