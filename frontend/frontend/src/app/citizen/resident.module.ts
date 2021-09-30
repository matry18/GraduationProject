import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ResidentCreateFormComponent } from './components/resident-create-form/resident-create-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ResidentCreateDialogComponent } from './components/resident-create-dialog/resident-create-dialog.component';
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import { ResidentListComponent } from './components/resident-list/resident-list.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatTableModule} from "@angular/material/table";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {MatTooltipModule} from "@angular/material/tooltip";


@NgModule({
  declarations: [
    ResidentCreateDialogComponent,
    ResidentListComponent
  ],
  exports: [
    ResidentCreateDialogComponent,
    ResidentListComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatTableModule,
    MatInputModule,
    MatIconModule,
    MatTooltipModule,

  ]
})
export class ResidentModule { }
