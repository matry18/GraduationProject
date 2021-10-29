import { Injectable } from '@angular/core';
import {CurrentUserService} from "./current-user.service";
import {AccessRightDto, CurrentUserState, EmployeeDto} from "../typings";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {

  private currentUser: any | EmployeeDto;
  constructor(private currentUserService: CurrentUserService,
              private router: Router) {
    this.currentUserService.globalStateChanged.subscribe((currentUserState: CurrentUserState) => {
      this.currentUser = currentUserState.currentUser;
    });
  }

  public hasAccessRight(accessRights: string[]): boolean {

    console.log(accessRights);
    for (let i = 0; i< accessRights.length; i++) {
      if(this.currentUser.roleDto.accessRights.map((accessRight: AccessRightDto) => accessRight.name).includes(accessRights[i])) {
        accessRights = [];
        console.log("I have access");
        return true;
      }
    }
    accessRights = [];
    return false;
  }

  public hasRole(roleNames: Array<string>) {
    return roleNames?.includes(this.currentUser?.roleDto?.name);
  }

  public redirectIfRoleIsMissing(roleNames: Array<string>): void {
    if(!this.hasRole(roleNames)) {
      this.router.navigate(["/"]);
    }
  }

}
