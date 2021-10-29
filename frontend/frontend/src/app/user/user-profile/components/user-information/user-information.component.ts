import {Component, OnInit} from '@angular/core';
import {CurrentUserService} from "../../../../shared-services/current-user.service";
import {EmployeeDto} from "../../../../typings";
import {BehaviorSubject} from "rxjs";

@Component({
  selector: 'user-information',
  templateUrl: './user-information.component.html',
  styleUrls: ['./user-information.component.css']
})
export class UserInformationComponent implements OnInit {
  private currentUserSubject = new BehaviorSubject<any | EmployeeDto>(null);
  public currentUser: any | EmployeeDto;

  constructor(private currentUserService: CurrentUserService) {
  }

  public ngOnInit(): void {
    this.currentUserService.globalStateChanged.subscribe((state) => {
      this.currentUserSubject.next(state.currentUser);
      this.currentUser = this.currentUserSubject.getValue();
    });

  }

}
