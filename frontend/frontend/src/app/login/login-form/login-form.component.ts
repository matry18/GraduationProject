import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BehaviorSubject} from "rxjs";
import {AuthenticationService} from "../../shared-services/authentication.service";
import {Router} from "@angular/router";
import {CurrentUserService} from "../../shared-services/current-user.service";
import {CurrentUserState, EmployeeDto} from "../../typings";

@Component({
  selector: 'login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  public loginForm: FormGroup;
  public invalidLoginAttempt: boolean = false;
  private loggedIn = new BehaviorSubject<boolean>(false);

  constructor(private fb: FormBuilder,
              private authenticationService: AuthenticationService,
              private router: Router) {

    this.loginForm = this.fb.group({
      employeeId:[null],
      username: ["", Validators.required],
      password: ["", Validators.required]
      }
    );
  }

  public ngOnInit(): void {
    this.authenticationService.globalStateChanged.subscribe((state) => {
      this.loggedIn.next(state.logged_in);
    });

    if(this.loggedIn.getValue()) {
      this.router.navigate(["/"]);
    } else {
      this.router.navigate(["/login"]);
    }


  }

  public login(): void {
    this.authenticationService.login(this.loginForm.value);
    this.wasCredentialsNotValid();
  }

  private wasCredentialsNotValid(): void {
    this.invalidLoginAttempt =  !this.authenticationService.isAuthenticated();
  }

  public cancel(): void {
    this.loginForm.reset();
    this.invalidLoginAttempt = false;
  }

}
