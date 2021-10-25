import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../../shared-services/authentication.service";
import {BehaviorSubject} from "rxjs";
import {CurrentUserState, EmployeeDto} from "../../../typings";
import {Router} from "@angular/router";
import {CurrentUserService} from "../../../shared-services/current-user.service";

@Component({
  selector: 'general-navbar',
  templateUrl: './general-navbar.component.html',
  styleUrls: ['./general-navbar.component.css']
})
export class GeneralNavbarComponent implements OnInit {

  public loggedIn = new BehaviorSubject<boolean>(false);
  public currentUser: any | EmployeeDto;

  constructor(private authenticationService: AuthenticationService,
              private currentUserService: CurrentUserService) {

    this.currentUserService.globalStateChanged.subscribe((currentUserState: CurrentUserState) => {
      this.currentUser = currentUserState.currentUser;
    });
  }

  public ngOnInit(): void {
    this.authenticationService.globalStateChanged.subscribe((state) => {
      this.loggedIn.next(state.logged_in);
    });



  }


  public logout(): void {
    this.authenticationService.logout();
  }
}
