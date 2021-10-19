import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./homepage/components/home/home.component";
import {ResidentListComponent} from "./citizen/components/resident-list/resident-list.component";
import {ErrorComponent} from "./errorpage/components/error/error.component";
import {UserProfileComponent} from "./user/user-profile/container/user-profile/user-profile.component";
import {DepartmentContainerComponent} from "./department/container/department-container/department-container.component";
import {SynchronizationErrorListComponent} from "./synchronization-error-log/synchronization-error-list/synchronization-error-list.component";
import {SynchronizationLogContainerComponent} from "./synchronization-error-log/synchronization-log-container/synchronization-log-container.component";

const routes: Routes = [];

@NgModule({
  imports: [
    RouterModule.forRoot([
      {path: '', component: HomeComponent},
      {path: 'department', component: DepartmentContainerComponent},
      {path: 'user/logged-in', component: UserProfileComponent},
      {path: 'citizen', component: ResidentListComponent},
      {path: 'synchronization-log', component: SynchronizationLogContainerComponent},
      {path: '**', component: ErrorComponent},])
    ,],
  exports: [RouterModule]
})
export class AppRoutingModule { }
