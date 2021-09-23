import { Component, OnInit } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {ResidentDto} from "../../../typings";
import {ResidentService} from "../../resident.service";
import {MatDialog} from "@angular/material/dialog";
import {ResidentCreateFormComponent} from "../resident-create-form/resident-create-form.component";
import {SnackbarService} from "../../../snackbars/FormSubmission/snackbar.service";


export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  {position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H'},
  {position: 2, name: 'Helium', weight: 4.0026, symbol: 'He'},
  {position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li'},
  {position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be'},
  {position: 5, name: 'Boron', weight: 10.811, symbol: 'B'},
  {position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C'},
  {position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N'},
  {position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O'},
  {position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F'},
  {position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne'},
];
@Component({
  selector: 'citizen-list',
  templateUrl: './resident-list.component.html',
  styleUrls: ['./resident-list.component.css']
})
export class ResidentListComponent implements OnInit {

  public citizenList: ResidentDto[] = [];

  displayedColumns = ['id', 'firstname', 'lastname', 'department', 'email', 'phoneNumber'];

    constructor(private citizenService: ResidentService, private dialog: MatDialog, private snackbarService: SnackbarService) { }

  ngOnInit(): void {
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

  public applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
  }

  public openEditDialog(residentDto: ResidentDto): void {
      this.dialog.open(ResidentCreateFormComponent,{
        data: {resident: residentDto}});
      this.dialog.afterAllClosed.subscribe(() =>
        this.fetchAllCitizen());
    }


}
