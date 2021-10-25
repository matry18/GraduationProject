import { Component, OnInit } from '@angular/core';
import {CurrentUserService} from "../../../../shared-services/current-user.service";
import {CurrentUserState, EmployeeDto} from "../../../../typings";

@Component({
  selector: 'user-information',
  templateUrl: './user-information.component.html',
  styleUrls: ['./user-information.component.css']
})
export class UserInformationComponent implements OnInit {
  public userInfo: string[] = ['Boots', 'Clogs', 'Loafers', 'Moccasins', 'Sneakers'];
  public currentUser: any | EmployeeDto;
  constructor(private currentUserService: CurrentUserService) { }

  public ngOnInit(): void {
    this.currentUserService.globalStateChanged.subscribe((currentUserState: CurrentUserState) => {
      this.currentUser = currentUserState.currentUser;
    });
  }

}
