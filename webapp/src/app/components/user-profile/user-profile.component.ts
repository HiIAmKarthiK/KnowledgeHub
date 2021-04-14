import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DashboardService } from 'src/app/services/dashboard.service';
import { FavouritesService } from 'src/app/services/favourites.service';
import { LoginService } from 'src/app/services/login.service';
import { Neo4jService } from 'src/app/services/neo4j/neo4j.service';
import { UserProfileService } from 'src/app/services/user-profile.service';
import { SharedService } from 'src/app/shared/shared.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css'],
})
export class UserProfileComponent implements OnInit {
  constructor(
    private service: UserProfileService,
    private activateRoute: ActivatedRoute,
    private loginservice: LoginService,
    private shared: SharedService,
    private router: Router,
    private neo4jService: Neo4jService,
    private favouriteService: FavouritesService,
    private dashboardService: DashboardService
  ) {}
  userName: any;
  useremail: any;
  profilepic: any;
  details: any;
  favouriteBooks: any;
  mBooks: any;
  userRole: any;
  ngOnInit(): void {
    this.getuserdetails();
    this.getFavouriteBooks();
    this.getBooksFromMongo();
    this.getUserRole();
  }
  getuserdetails() {
    this.neo4jService.email.subscribe((email) => {
      this.useremail = email;
      console.log('useremail :', this.useremail);
    });
    this.service.getuser(this.useremail).subscribe((data) => {
      this.details = data;
      console.log(data);
    });
  }

  getUserRole() {
    let localRole = localStorage.getItem('role');
    if (localRole == 'NormalUser') {
      this.userRole = 'Normal';
    } else {
      this.userRole = 'Premium';
    }
  }

  getFavouriteBooks() {
    let email = localStorage.getItem('userEmail');
    this.favouriteService.getAllFavouriteBooks(email).subscribe((data) => {
      console.log(data);

      this.favouriteBooks = data;
    });
  }

  getBooksFromMongo() {
    this.dashboardService.getAllBooks().subscribe((data) => {
      this.mBooks = data;
    });
  }

  getBookImage(passedBook) {
    for (let book of this.mBooks) {
      if (book.bookTitle == passedBook.bookTitle) {
        return book.imageByte;
      }
    }
  }
}
