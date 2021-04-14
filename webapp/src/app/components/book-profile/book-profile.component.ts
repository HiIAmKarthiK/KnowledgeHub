import { AfterViewInit, ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatAccordion } from '@angular/material/expansion';
import {
  ActivatedRoute,
  ActivatedRouteSnapshot,
  Router,
} from '@angular/router';
import { title } from 'process';
import { BookPathServicesService } from 'src/app/services/book-path-services.service';
import { BookProfileServiceService } from 'src/app/services/book-profile-service.service';
import { DashboardService } from 'src/app/services/dashboard.service';
import { FavouritesService } from 'src/app/services/favourites.service';
import { LoginService } from 'src/app/services/login.service';
import { Neo4jService } from 'src/app/services/neo4j/neo4j.service';
import { RegisterService } from 'src/app/services/register.service';
import { UserProfileService } from 'src/app/services/user-profile.service';
import { SharedService } from 'src/app/shared/shared.service';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { PaymentDialogComponent } from '../payment-dialog/payment-dialog.component';
// import { saveAs} from 'file-saver';
// import * as fileSaver from 'file-saver';
@Component({
  selector: 'app-book-profile',
  templateUrl: './book-profile.component.html',
  styleUrls: ['./book-profile.component.css'],
})
export class BookProfileComponent implements OnInit {
  useremail: any;
  imgdetails: any;
  profilepic: any;
  name: any;
  Userdetails: any;
  pfp: any;
  isFavourite: boolean = false;
  userrole: string;
  role: boolean = false;
  amount: any = 60;
  premiumUserDetails: any;
  premiumUser: boolean = false;
  constructor(
    private router: Router,
    private bookpathservicesservice: BookPathServicesService,
    private service: BookProfileServiceService,
    private title: DashboardService,
    private loginservice: LoginService,
    private shared: SharedService,
    private activateRoute: ActivatedRoute,
    private neo4jService: Neo4jService,
    private userservice: UserProfileService,
    private service1: UserProfileService,
    private favouriteService: FavouritesService,
    private register: RegisterService,
    public dialog: MatDialog
  ) {}

  snapshot: ActivatedRouteSnapshot;
  bookTitle = this.activateRoute.snapshot.params.bookTitle;
  authorName: any;
  language: any;
  bookGenre: any;
  description: any;
  uploadedOn: any;
  coverimage: any;
  userName: any;
  comment: any;
  details: any;
  updateddetails: any;
  totalDownloads: any;
  totalViews: any;

  userdetails: any;
  bookurl: any;
  file: any;
  currentRate: any = 0;
  submittedcomment: boolean;
  show = false;
  callChild = false;
  length: any;
  avgRating: any = 0;

  getbookdetails() {
    this.service.getbookdetails(this.bookTitle).subscribe((data) => {
      this.details = data;
      this.file = this.details.file;
      console.log(data);
      this.callChild = true;
      let sum = 0;
      this.length = this.details.commentSections.length;
      for (let i = 0; i < this.length; i++) {
        sum += this.details.commentSections[i].rating;
      }
      this.avgRating = Math.ceil(sum / this.length);
      console.log(this.length);
    });
  }

  ngOnInit(): void {
    this.getbookdetails();
    this.getuserdetails();
    this.getTheBookFromNeo();
    this.invokeStripe();

    this.bookurl = '/assets/The-Sign-of-the-Four.pdf';
    this.checkIfFavourite();
    // this.getUserId();
    this.useremail = localStorage.getItem('userEmail');
    console.log(this.useremail);
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

  onSumitComments() {
    console.log(this.comment);
    if (this.comment == '' || this.comment == null) {
      console.log('cant empty');
    } else {
      let commentDetails = {
        comment: this.comment,
        userName: this.name,
        pic: this.pfp,
        rating: this.currentRate,
      };

      this.service
        .addCommentsToBooks(this.bookTitle, commentDetails)
        .subscribe((data) => {
          this.getbookdetails();
          this.comment = '';
        });
    }
  }

  getuserdetails() {
    this.neo4jService.email.subscribe((email) => {
      this.useremail = email;
      console.log('useremail :', this.useremail);
    });
    this.service1.getuser(this.useremail).subscribe((data) => {
      this.Userdetails = data;
      this.name = this.Userdetails.userName;
      this.pfp = this.Userdetails.pic;
      this.userrole = this.Userdetails.role;

      document.getElementById('spinner').style.display = 'none';
    });
  }

  toBookProfile() {
    this.router.navigate([`/bookview/${this.bookTitle}`]);
    this.neo4jService.email.subscribe((email) => {
      if (email && this.bookTitle) {
        this.service
          .startReadingBook(email, this.bookTitle)
          .subscribe((data) => {
            console.log(data);
          });
      }
    });

    this.neo4jService.updateBookViews(this.bookTitle).subscribe((data) => {
      console.log(data);
    });
  }

  addToFavourites() {
    let email = localStorage.getItem('userEmail');
    this.favouriteService
      .addToFavourites(email, this.bookTitle)
      .subscribe((data) => {
        console.log(data);
        this.checkIfFavourite();
      });
  }

  checkIfFavourite() {
    let email = localStorage.getItem('userEmail');
    this.favouriteService
      .checkIfBookIsFavourite(email, this.bookTitle)
      .subscribe((data) => {
        if (data == 'Book is favourite') {
          this.isFavourite = true;
        } else {
          this.isFavourite = false;
        }
      });
  }

  unFavourite() {
    let email = localStorage.getItem('userEmail');
    this.favouriteService
      .unFavouriteBook(email, this.bookTitle)
      .subscribe((data) => {
        console.log(data);
        this.checkIfFavourite();
      });
  }

  getTheBookFromNeo() {
    this.neo4jService.getABook(this.bookTitle).subscribe((data: any) => {
      this.totalDownloads = data.totalDownloads;
      this.totalViews = data.totalViews;
    });
  }

  downloadFile() {
    if (this.userrole == 'Premium') {
      this.service.downloadFile(this.bookTitle).subscribe((response) => {
        let blob: any = new Blob([response], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);

        const link = document.createElement('a');
        link.setAttribute('target', '_blank');
        link.setAttribute('href', url);
        link.setAttribute('download', this.file);
        document.body.appendChild(link);
        link.click();
        link.remove();
        this.neo4jService.updateDownloads(this.bookTitle).subscribe((data) => {
          console.log(data);
        });
      }),
        (error) => console.log('Error downloading the file'),
        () => console.info('File downloaded successfully');
    } else {
      // open a modal window, and show the message not a premium user yet
      this.role = true;
      console.log('Not a premium user yet.');
    }
  }

  getRate(rate: any) {
    return new Array(Number(rate));
  }
  onPay() {
    this.onCheckout(() => {
      this.changeUserRole();
      this.openPaymentDialog();
    });
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
    console.log('email:', this.useremail);
    this.register.changeRoleOfUser(this.useremail).subscribe((data) => {
      console.log('Role updated');
      this.premiumUser = true;
      (this.userrole = 'Premium'), localStorage.setItem('role', this.userrole);
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
  openPaymentDialog() {
    this.role = false;
    const dialogRef = this.dialog.open(PaymentDialogComponent, {
      width: '500px',
    });
  }
}
