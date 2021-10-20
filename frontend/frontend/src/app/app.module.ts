import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CovalentLayoutModule } from '@covalent/core/layout';
import { CovalentStepsModule  } from '@covalent/core/steps';
import { CovalentHighlightModule } from '@covalent/highlight';
import { CovalentMarkdownModule } from '@covalent/markdown';
import { CovalentDynamicFormsModule } from '@covalent/dynamic-forms';
import { CovalentBaseEchartsModule } from '@covalent/echarts/base';
import { MatIconModule } from '@angular/material/icon';
import { CovalentUserProfileModule } from '@covalent/core/user-profile';



import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreateUserComponent } from './CreateUser/create-user/create-user.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatListModule} from '@angular/material/list';
import { ResidentCreateFormComponent } from './citizen/components/resident-create-form/resident-create-form.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {A11yModule} from '@angular/cdk/a11y';
import {ClipboardModule} from '@angular/cdk/clipboard';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {PortalModule} from '@angular/cdk/portal';
import {ScrollingModule} from '@angular/cdk/scrolling';
import {CdkStepperModule} from '@angular/cdk/stepper';
import {CdkTableModule} from '@angular/cdk/table';
import {CdkTreeModule} from '@angular/cdk/tree';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatBadgeModule} from '@angular/material/badge';
import {MatBottomSheetModule} from '@angular/material/bottom-sheet';
import {MatButtonModule} from '@angular/material/button';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {MatCardModule} from '@angular/material/card';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatChipsModule} from '@angular/material/chips';
import {MatStepperModule} from '@angular/material/stepper';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatDialogModule} from '@angular/material/dialog';
import {MatDividerModule} from '@angular/material/divider';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatInputModule} from '@angular/material/input';
import {MatMenuModule} from '@angular/material/menu';
import {MatNativeDateModule, MatRippleModule} from '@angular/material/core';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatRadioModule} from '@angular/material/radio';
import {MatSelectModule} from '@angular/material/select';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatSliderModule} from '@angular/material/slider';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatSortModule} from '@angular/material/sort';
import {MatTableModule} from '@angular/material/table';
import {MatTabsModule} from '@angular/material/tabs';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatTreeModule} from '@angular/material/tree';
import {OverlayModule} from '@angular/cdk/overlay';
import {ReactiveFormsModule} from "@angular/forms";
import {FormSubmissionPositiveComponent} from "./snackbars/FormSubmission/form-submission-positive/form-submission-positive.component";
import { FormSubmissionNegativeComponent } from './snackbars/FormSubmission/form-submission-negative/form-submission-negative.component';
import {ResidentModule} from "./citizen/resident.module";
import {EmployeeModule} from "./employee/employee.module";
import { AddUserDialogButtonComponent } from './user/add-user-dialog/add-user-dialog-button.component';
import { AddUserMenuComponent } from './user/add-user-menu/add-user-menu.component';
import { GeneralNavbarComponent } from './navbar/components/general-navbar/general-navbar.component';
import { HomeComponent } from './homepage/components/home/home.component';
import {RouterModule} from "@angular/router";
import {ResidentListComponent} from "./citizen/components/resident-list/resident-list.component";
import {EmployeeListComponent} from "./employee/components/employee-list/employee-list.component";
import { ErrorComponent } from './errorpage/components/error/error.component';
import { UserProfileComponent } from './user/user-profile/container/user-profile/user-profile.component';
import { UserInformationComponent } from './user/user-profile/components/user-information/user-information.component';
import { ProfilePictureComponent } from './user/user-profile/components/profile-picture/profile-picture.component';
import { DepartmentContainerComponent } from './department/container/department-container/department-container.component';
import { DepartmentListComponent } from './department/components/department-list/department-list.component';
import { CreateDepartmentFormComponent } from './department/components/create-department/create-department-form.component';
import { CreateDepartmentDialogButtonComponent } from './department/components/create-department-dialog-button/create-department-dialog-button.component';
import { DepartmentSelectorComponent } from './department/components/department-selector/department-selector.component';
import { SynchronizationErrorListComponent } from './synchronization-error-log/synchronization-error-list/synchronization-error-list.component';
import { SynchronizationLogContainerComponent } from './synchronization-error-log/synchronization-log-container/synchronization-log-container.component';
import { FailedSynchronizationResidentListComponent } from './synchronization-error-log/failed-synchronization-resident-list/failed-synchronization-resident-list.component';
import {EmployeeCreateFormComponent} from "./employee/components/employee-create-form/employee-create-form.component";


@NgModule({
  declarations: [
    AppComponent,
    CreateUserComponent,
    ResidentCreateFormComponent,
    FormSubmissionPositiveComponent,
    FormSubmissionNegativeComponent,
    AddUserDialogButtonComponent,
    AddUserMenuComponent,
    GeneralNavbarComponent,
    HomeComponent,
    ErrorComponent,
    UserProfileComponent,
    UserInformationComponent,
    ProfilePictureComponent,
    DepartmentContainerComponent,
    DepartmentListComponent,
    CreateDepartmentFormComponent,
    CreateDepartmentDialogButtonComponent,
    DepartmentSelectorComponent,
    SynchronizationErrorListComponent,
    SynchronizationLogContainerComponent,
    FailedSynchronizationResidentListComponent,


  ],
  imports: [
    MatInputModule,
    A11yModule,
    MatListModule,
    CovalentLayoutModule,
    CovalentStepsModule,
    BrowserModule,
    AppRoutingModule,
    CovalentHighlightModule,
    CovalentMarkdownModule,
    CovalentDynamicFormsModule,
    CovalentBaseEchartsModule,
    BrowserAnimationsModule,
    MatIconModule,
    CovalentUserProfileModule,
    MatFormFieldModule,
    A11yModule,
    ClipboardModule,
    CdkStepperModule,
    CdkTableModule,
    CdkTreeModule,
    DragDropModule,
    MatAutocompleteModule,
    MatBadgeModule,
    MatBottomSheetModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatStepperModule,
    MatDatepickerModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    MatTreeModule,
    OverlayModule,
    PortalModule,
    ScrollingModule,
    ReactiveFormsModule,
    ResidentModule,
    EmployeeModule,


  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
