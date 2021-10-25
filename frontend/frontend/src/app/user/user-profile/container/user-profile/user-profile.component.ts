import { Component, OnInit } from '@angular/core';
import {CurrentUserState, EmployeeDto} from "../../../../typings";
import {CurrentUserService} from "../../../../shared-services/current-user.service";

@Component({
  selector: 'user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  public currentUser: any | EmployeeDto;
  constructor(private currentUserService: CurrentUserService) { }

  public ngOnInit(): void {
    this.currentUserService.globalStateChanged.subscribe((currentUserState: CurrentUserState) => {
      this.currentUser = currentUserState.currentUser;
    });
  }
}
