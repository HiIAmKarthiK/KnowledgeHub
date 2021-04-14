import { PaymentDialogComponent } from './components/payment-dialog/payment-dialog.component';
import { ViewNotesComponent } from './components/view-notes/view-notes.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LandingComponent } from './components/landing/landing.component';
import { LoginComponent } from './components/login/login.component';
import { BookReaderComponent } from './components/book-reader/book-reader.component';
import { UploadingbookComponent } from './components/uploadingbook/uploadingbook.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { BookProfileComponent } from './components/book-profile/book-profile.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { FavouritesComponent } from './components/favourites/favourites.component';
import { SpeechToTextComponent } from './components/speech-to-text/speech-to-text.component';
import { TextSpeechComponent } from './components/text-speech/text-speech.component';

const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: 'bookview/:bookTitle', component: BookReaderComponent },
  { path: 'uploadbook', component: UploadingbookComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'viewnote', component: ViewNotesComponent },
  { path: 'userprofile', component: UserProfileComponent },
  { path: 'bookprofile/:bookTitle', component: BookProfileComponent },
  { path: 'favourites', component: FavouritesComponent },
  { path: 'speech', component: SpeechToTextComponent},
  { path: 'speak', component: TextSpeechComponent},
 
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
})
export class AppRoutingModule {}
