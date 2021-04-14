import { FormGroup, FormControl } from '@angular/forms';
import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { BookPathServicesService } from 'src/app/services/book-path-services.service';
import { ThemePalette } from '@angular/material/core';
import { SharedService } from '../../shared/shared.service';
import { MatDialog } from '@angular/material/dialog';
import { DictionaryComponent } from '../dictionary/dictionary.component';
import { ActivatedRoute, Router } from '@angular/router';
import { BookProfileServiceService } from 'src/app/services/book-profile-service.service';
@Component({
  selector: 'app-book-reader',
  templateUrl: './book-reader.component.html',
  styleUrls: ['./book-reader.component.css'],
})
export class BookReaderComponent implements OnInit {
  constructor(
    private pdfBookService: BookPathServicesService,
    private location: Location,
    private shared: SharedService,
    public dialog: MatDialog,
    private router: Router,
    private activateRoute: ActivatedRoute,
    private service: BookProfileServiceService
  ) {}

  color: ThemePalette = 'accent';
  email = this.activateRoute.snapshot.params.email;
  userEmail: any;
  pdfMode: boolean = false;
  page: any = 3;
  jumPage: number;
  loadBook: boolean = false;
  pdfSrc: any = '';
  isDisabled: boolean = false;
  innerbtn: boolean = false;
  lastpage: number = 107;
  notes: string;
  sent: Boolean = false;
  val: Boolean = false;
  notesData: any;
  bookdetails: any;
  bookTitle = this.activateRoute.snapshot.params.bookTitle;
  ngOnInit(): void {
    //this.pdfSrc = '/assets/The-Sign-of-the-Four.pdf';
    // this.pdfSrc=this.bookdetails.bookUrl;
    // console.log(this.bookdetails.bookUrl);

    this.userEmail = localStorage.getItem('userEmail');
    this.getNotesData();
    console.log(this.shared.getMessage());
    this.getbookPath();
    this.getLastPage();
  }

  getLastPage() {
    this.pdfBookService
      .getLastPageRead(this.userEmail, this.bookTitle)
      .subscribe((data) => {
        this.page = data;
        this.loadBook = true;
        if (this.pdfSrc != null) {
          document.getElementById('spinner').style.display = 'none';
        }
      });
  }

  getNotesData() {
    this.pdfBookService.getAllNotes(this.userEmail).subscribe((data) => {
      this.notesData = data;
    });
  }

  setPage() {
    if (this.jumPage === null || this.jumPage === undefined) {
      window.alert('fill the page No');
    } else if (this.jumPage >= 0 && this.jumPage <= this.lastpage) {
      this.page = this.jumPage;
    } else {
      window.alert(
        `the page Value mus be less than or equal to ${this.lastpage}`
      );
    }
  }

  pageNext() {
    if (this.page <= this.lastpage) {
      this.page++;
    }
  }

  pagePrev() {
    if (this.page > 0) this.page--;
  }

  onFileSelected() {
    let img: any = document.querySelector('#file');

    if (typeof FileReader !== 'undefined') {
      let reader = new FileReader();
      reader.onload = (e: any) => {
        this.pdfSrc = e.target.result;
      };
      reader.readAsArrayBuffer(img.files[0]);
    }
  }
  getbookPath() {
    console.log('book title:', this.bookTitle);
    this.pdfBookService.getBookPath(this.bookTitle).subscribe((data) => {
      console.log('book details:', data);
      this.bookdetails = data;

      this.pdfSrc = this.bookdetails.bookUrl;
      console.log(this.bookdetails.bookUrl);
    });
  }
  pdfModeswitch(state: boolean) {
    this.pdfMode = state;
  }
  perviouspage() {
    this.sendLastPage(false);
  }

  goToDashboard() {
    this.sendLastPage(true);
  }

  autoTicks = true;
  disabled = false;
  invert = false;
  max = 1.5;
  min = 0;
  showTicks = false;
  step = 0.08;
  thumbLabel = false;
  value = 1;
  vertical = false;
  tickInterval = 1;

  getSliderTickInterval(): number | 'auto' {
    if (this.showTicks) {
      return this.autoTicks ? 'auto' : this.tickInterval;
    }
    return 0;
  }

  sendNote() {
    console.log(this.userEmail, this.page, this.notes);
    let previousRead = {
      bookTitle: this.bookTitle,
      bookAuthor: this.bookdetails.authorName,
      bookDescription: 'Biography ',
      notes: [{ notes: this.notes, page: this.page }],
      lastpage: this.page,
    };
    try {
      if (this.notes.length != 0) {
        this.pdfBookService
          .updateNotes(this.userEmail, previousRead)
          .subscribe((data) => {
            this.sent = true;
            this.getNotesData();
            this.notes = '';
          });
      }
    } catch (error) {
      this.val = true;
    }
  }

  sendLastPage(toDashboard) {
    console.log('Left reading', this.bookTitle, ' at page:', this.page);
    let previousRead = {
      bookTitle: this.bookTitle,
      bookAuthor: this.bookdetails.authorName,
      bookDescription: 'Biography ',
      lastpage: this.page,
    };
    try {
      this.pdfBookService
        .updateLastPage(this.userEmail, previousRead)
        .subscribe((data) => {
          if (toDashboard) {
            this.router.navigate(['/dashboard']);
          } else {
            this.location.back();
          }
        });
    } catch (error) {
      this.val = true;
    }
  }

  closedAlert() {
    this.sent = false;
  }
  resetTextarea() {
    this.val = false;
  }
  openDialog(): void {
    const dialogRef = this.dialog.open(DictionaryComponent, {});
    dialogRef.afterClosed().subscribe((result) => {});
  }
  backtodashboard() {
    this.router.navigateByUrl(`dashboard/${this.userEmail}`);
  }
  viewnote() {
    this.router.navigate([`/viewnote`]);
  }
}
