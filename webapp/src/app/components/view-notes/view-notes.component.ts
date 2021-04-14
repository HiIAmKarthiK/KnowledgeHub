import { Component, OnInit, AfterViewInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { BookPathServicesService } from 'src/app/services/book-path-services.service';
import { SharedService } from 'src/app/shared/shared.service';
import { switchMap, debounceTime, tap, map } from 'rxjs/operators';
import { Location } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
  selector: 'app-view-notes',
  templateUrl: './view-notes.component.html',
  styleUrls: ['./view-notes.component.css'],
})
export class ViewNotesComponent implements OnInit {
  constructor(
    private services: BookPathServicesService,
    private shared: SharedService,
    private location: Location,
    private router: Router,
    private activateRoute: ActivatedRoute
  ) {}
  email = this.activateRoute.snapshot.params.email;
  public mineData: any;
  public userEmail: any;
  public notesData: any;
  nameSearch: string = '';
  name = new FormControl('');
  ngOnInit(): void {
    this.userEmail = localStorage.getItem('userEmail');
    this.services.getAllNotes(this.userEmail).subscribe((data) => {
      this.notesData = data;
      console.log(data);
    });
    // this.name.valueChanges.pipe(debounceTime(1000)).subscribe(console.log);
  }

  perviouspage() {
    this.location.back();
  }

  ngAfterViewInit() {
    const ids: any = document.getElementById('accordionExample');
    ids.addEventListener('show.bs.collapse', (e: any) => {
      e.className += 'opened';
      e.target.parentElement.classList.add('opened');
      // console.log(e);
    });
    ids.addEventListener('hide.bs.collapse', (e: any) => {
      e.target.parentElement.classList.remove('opened');
      // console.log(e);
    });
  }
  // backtodashboard() {
  //   this.router.navigateByUrl(`dashboard/${this.email}`);
  // }
}
