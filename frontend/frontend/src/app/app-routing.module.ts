import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./homepage/components/home/home.component";
import {ResidentListComponent} from "./citizen/components/resident-list/resident-list.component";
import {EmployeeListComponent} from "./employee/components/employee-list/employee-list.component";
import {ErrorComponent} from "./errorpage/components/error/error.component";
import {UserProfileComponent} from "./user/user-profile/container/user-profile/user-profile.component";
import {DepartmentContainerComponent} from "./department/container/department-container/department-container.component";
import {SynchronizationLogContainerComponent} from "./synchronization-error-log/synchronization-log-container/synchronization-log-container.component";
import {LoginFormComponent} from "./login/login-form/login-form.component";
import {AuthenticationRouteGuardService as AuthGuard} from "./shared-services/authentication-route-guard.service";

const routes: Routes = [];

@NgModule({
  imports: [
    RouterModule.forRoot([
      {path: '', component: HomeComponent, canActivate: [AuthGuard]},
      {path: 'department', component: DepartmentContainerComponent, canActivate: [AuthGuard]},
      {path: 'user/logged-in', component: UserProfileComponent, canActivate: [AuthGuard]},
      {path: 'citizen', component: ResidentListComponent, canActivate: [AuthGuard]},
      {path: 'login', component: LoginFormComponent},
      {path: 'synchronization-log', component: SynchronizationLogContainerComponent, canActivate: [AuthGuard]},
      {path: 'employee', component: EmployeeListComponent, canActivate: [AuthGuard]},
      {path: '**', component: ErrorComponent},])
    ,],
  exports: [RouterModule]
})
export class AppRoutingModule { }
