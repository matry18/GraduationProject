import { Component, OnInit } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {ResidentDto} from "../../../typings";
import {ResidentService} from "../../resident.service";
import {MatDialog} from "@angular/material/dialog";
import {ResidentCreateFormComponent} from "../resident-create-form/resident-create-form.component";
import {SnackbarService} from "../../../snackbars/FormSubmission/snackbar.service";

@Component({
  selector: 'citizen-list',
  templateUrl: './resident-list.component.html',
  styleUrls: ['./resident-list.component.css']
})
export class ResidentListComponent implements OnInit {

  public citizenList: ResidentDto[] = [];

  displayedColumns = ['id', 'firstname', 'lastname', 'department', 'email', 'phoneNumber'];

  constructor(private citizenService: ResidentService, private dialog: MatDialog, private snackbarService: SnackbarService) { }

  public ngOnInit(): void {
    this.fetchAllCitizen();
  }

  private fetchAllCitizen(): void {
     this.citizenService.getAllCitizen().subscribe((citizenList: ResidentDto[]) => {
      this.citizenList = citizenList;
     });

  }

  public deleteCitizen(citizenId: string): void {
      this.citizenService.deleteCitizen(citizenId).subscribe(
        ()=> {
          this.fetchAllCitizen();
          this.snackbarService.openSuccessfulSnackBar();
        },
        () => {this.snackbarService.openFailedSnackBar();}
      );
  }

  public openEditDialog(residentDto: ResidentDto): void {
      this.dialog.open(ResidentCreateFormComponent,{
        data: {resident: residentDto}});
      this.dialog.afterAllClosed.subscribe(() =>
        this.fetchAllCitizen());
    }


}
