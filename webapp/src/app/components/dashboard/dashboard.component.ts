import { MatDialog } from '@angular/material/dialog';
import {
  AfterViewInit,
  Component,
  DoCheck,
  OnDestroy,
  OnInit,
} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DashboardService } from 'src/app/services/dashboard.service';
import { FavouritesService } from 'src/app/services/favourites.service';
import { Neo4jService } from 'src/app/services/neo4j/neo4j.service';
import { RegisterService } from 'src/app/services/register.service';
import { SearchServiceService } from 'src/app/services/search-service.service';
import { UserProfileService } from 'src/app/services/user-profile.service';
import { SpeechDialogComponent } from '../speech-dialog/speech-dialog.component';
import { SharedService } from 'src/app/shared/shared.service';
import { CheckboxControlValueAccessor } from '@angular/forms';
import { PaymentDialogComponent } from '../payment-dialog/payment-dialog.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit, AfterViewInit {
  Userdetails: any;
  pfp: any;
  amount: any = 60;
  premiumUserDetails: any;
  role: any;
  premiumUser: boolean= false;
  constructor(
    private service: SearchServiceService,
    private dashboardService: DashboardService,
    private router: Router,
    private neo4jService: Neo4jService,
    private activateRoute: ActivatedRoute,
    private service2: UserProfileService,
    private favouriteService: FavouritesService,
    private register: RegisterService,
    public dialog: MatDialog,
    public SharedService: SharedService
  ) {}

  ngOnInit(): void {
    this.usermail = localStorage.getItem('userEmail');
    this.getAllBooks();
    this.getAllBooksFromNeo();
    this.getuserdetails();
    this.getRecommendedBooks();
    this.getAllFavouriteBooks();
    this.getLastReadBooks();
    this.invokeStripe();
    (function (d, m) {
      var kommunicateSettings = {
        appId: '47585bcc8d9061ce232d246d8eb3ce50',
        popupWidget: true,
        automaticChatOpenOnNavigation: true,
      };

      var s = document.createElement('script');
      s.type = 'text/javascript';
      s.async = true;
      s.src = 'https://widget.kommunicate.io/v2/kommunicate.app';
      var h = document.getElementsByTagName('head')[0];
      h.appendChild(s);
      (window as any).kommunicate = m;
      m._globals = kommunicateSettings;
    })(document, (window as any).kommunicate || {});
  }

  ngAfterViewInit(): void {
    document.getElementById('spinner').style.display = 'none';
  }

  public searchData: any;
  public userName: String;
  public datas: any;
  public books: any;
  public neoBooks: any;
  public thrillerBooks: any;
  public techBooks: any;
  public actionBooks: any;
  public horrorBooks: any;
  public comicBooks: any;
  public romanceBooks: any;
  public favouriteBooks: any;
  public lastReadBooks: any;
  public genreArray = [
    'thrillerBooks',
    'techBooks',
    'actionBooks',
    'horrorBooks',
    'comicBooks',
    'romanceBooks',
  ];
  public userFavourite: any;
  public recommendedBooks = [];
  public usermail;
  getSearchData() {
    this.service.getSearchedData(this.searchData).subscribe((data) => {
      this.datas = data;
      console.log(data);
    });
  }

  getRecommendedBooks() {
    this.neo4jService.email.subscribe((email) => {
      this.dashboardService
        .getRecommendedBooks(email)
        .subscribe((data: any) => {
          console.log(data);
          this.recommendedBooks = data;
        });
    });
  }

  getAllBooks() {
    this.dashboardService.getAllBooks().subscribe((data) => {
      this.books = data;
      this.thrillerBooks = this.books.filter((doc) =>
        doc.bookGenre.includes('Thriller')
      );
      this.techBooks = this.books.filter((doc) =>
        doc.bookGenre.includes('Tech')
      );
      this.actionBooks = this.books.filter((doc) =>
        doc.bookGenre.includes('Action')
      );
      this.horrorBooks = this.books.filter((doc) =>
        doc.bookGenre.includes('Horror')
      );
      this.comicBooks = this.books.filter((doc) =>
        doc.bookGenre.includes('Comic')
      );
      this.romanceBooks = this.books.filter((doc) =>
        doc.bookGenre.includes('Romance')
      );

      // console.log(this.romanceBooks);
    });
  }

  getAllBooksFromNeo() {
    this.neo4jService.getAllBooks().subscribe((data) => {
      this.neoBooks = data;
    });
  }

  getAllFavouriteBooks() {
    let email = localStorage.getItem('userEmail');
    this.favouriteService.getAllFavouriteBooks(email).subscribe((data) => {
      console.log(data);
      this.favouriteBooks = data;
    });
  }

  getLastReadBooks() {
    let email = localStorage.getItem('userEmail');
    this.neo4jService.getLastReadBooks(email).subscribe((data) => {
      this.lastReadBooks = data;
      console.log(this.lastReadBooks);
    });
  }

  checkIfFavourite(passedBook) {
    for (let book of this.favouriteBooks) {
      if (book.bookTitle == passedBook.bookTitle) {
        return true;
      }
    }

    return false;
  }

  getBookViews(passedBook) {
    for (let book of this.neoBooks) {
      if (book.bookTitle == passedBook.bookTitle) {
        return book.totalViews;
      }
    }
  }

  getBookImage(passedBook) {
    for (let book of this.books) {
      if (book.bookTitle == passedBook.bookTitle) {
        return book.imageByte;
      }
    }
  }

  getBookDownloads(passedBook) {
    for (let book of this.neoBooks) {
      if (book.bookTitle == passedBook.bookTitle) {
        return book.totalDownloads;
      }
    }
  }

  addToFavourites(book) {
    let email = localStorage.getItem('userEmail');

    this.favouriteService
      .addToFavourites(email, book.bookTitle)
      .subscribe((data) => {
        console.log(data);
        this.getAllFavouriteBooks();
      });
  }

  unfavourite(book) {
    let email = localStorage.getItem('userEmail');
    this.favouriteService
      .unFavouriteBook(email, book.bookTitle)
      .subscribe((data) => {
        console.log(data);
        this.getAllFavouriteBooks();
      });
  }

  toBookProfile(book) {
    this.router.navigateByUrl(`bookprofile/${book.bookTitle}`);
    console.log(book);

    // localStorage.setItem('bookTitle', title);
    // this.router.navigate(['/bookprofile'], { state: { bookTitle: title } });

    this.dashboardService.booktitle = book.bookTitle;
    // console.log(this.dashboardService.booktitle);
    // this.router.navigateByUrl(`bookprofile`);
  }

  toFavourites() {
    this.router.navigate(['/favourites']);
  }

  getuserdetails() {
    this.neo4jService.email.subscribe((email) => {
      this.usermail = email;
      console.log('useremail :', this.usermail);
    });
    this.service2.getuser(this.usermail).subscribe((data) => {
      console.log(data);
      this.Userdetails = data;
      this.pfp = this.Userdetails.pic;
      this.userName = this.Userdetails.userName;
      this.role = this.Userdetails.role;
      localStorage.setItem('role', this.role);
      if (this.role === 'Premium') {
        this.premiumUser=true;
      }
    });
  }

  logout() {
    this.router.navigate(['/']).then(() => {
      location.reload();
    });
  }

  // stripe
  onPay() {
    this.onCheckout(() => {
      this.changeUserRole();
      this.openPaymentDialog();
    })
  }

  onCheckout(_callback) {
    const paymentHandler = (<any>window).StripeCheckout.configure({
      key:
        'pk_test_51IZBomSHRrRTSEpbuQrOPFiaLDWme9S5K31K97hAhc9honzBGfp7lBuNcTn6b0wMcPIyuUehxWt9ExS1hLqi4wt000p7J7RxPS',

      locale: 'auto',
      Attr: 'data-currency=INR',
      token: function (stripeToken: any) {
        this.premiumUserDetails = stripeToken;
        console.log(this.premiumUserDetails);
        _callback();
      },
    });

    paymentHandler.open({
      name: 'Knowledge Hub',
      description: 'Subcribers payment',
      amount: this.amount * 100,
    });
    // this.changeUserRole();
  }

  changeUserRole() {
    console.log("email:", this.usermail);
    this.register.changeRoleOfUser(this.usermail).subscribe((data) => {
      console.log("Role updated");
      this.premiumUser=true;
      this.role="Premium",
      localStorage.setItem('role', this.role);
    });
  }

  invokeStripe() {
    if (!window.document.getElementById('stripe-script')) {
      const script = window.document.createElement('script');
      script.id = 'stripe-script';
      script.type = 'text/javascript';
      script.src = 'https://checkout.stripe.com/checkout.js';

      window.document.body.appendChild(script);
    }
  }

  openDialog() {
    const dialogRef = this.dialog.open(SpeechDialogComponent, {
      width: '500px',
      data: {
        voiceText: '',
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.searchData = result.voiceText;
      this.getSearchData();
    });
  }

  openPaymentDialog() {
    const dialogRef = this.dialog.open(PaymentDialogComponent, {
      width: '500px',
    });

    dialogRef.afterClosed().subscribe();
  }
}
