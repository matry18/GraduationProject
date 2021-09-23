import { Injectable } from '@angular/core';
import {FormSubmissionPositiveComponent} from "./form-submission-positive/form-submission-positive.component";
import {FormSubmissionNegativeComponent} from "./form-submission-negative/form-submission-negative.component";
import {MatSnackBar} from "@angular/material/snack-bar";

const durationInSeconds = 1.5 * 1000;
@Injectable({
  providedIn: 'root'
})
export class SnackbarService {

  constructor(private _snackBar: MatSnackBar) { }

 public openSuccessfulSnackBar() {

    this._snackBar.openFromComponent(FormSubmissionPositiveComponent, {
      duration: durationInSeconds,
    });
  }

  public openFailedSnackBar() {

    this._snackBar.openFromComponent(FormSubmissionNegativeComponent, {
      duration: durationInSeconds,
    });
  }
}
