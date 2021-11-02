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
    accessRights.push("admin");
    for (let i = 0; i< accessRights.length; i++) {
      if(this.currentUser?.roleDto?.accessRights.map((accessRight: AccessRightDto) => accessRight.name).includes(accessRights[i])) {
        accessRights = [];
        return true;
      }
    }
    accessRights = [];
    return false;
  }

  public hasRole(roleNames: Array<string>) {
    return roleNames?.includes(this.currentUser?.roleDto?.name);
  }

  public redirectIfAccessRightIsMissing(accessRights: Array<string>): void {
    if(!this.hasAccessRight(accessRights)) {
      this.router.navigate(['']);
    }
  }

}
