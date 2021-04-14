import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-payment-dialog',
  templateUrl: './payment-dialog.component.html',
  styleUrls: ['./payment-dialog.component.css']
})
export class PaymentDialogComponent implements OnInit {

  constructor(public paymentDialog: MatDialogRef<PaymentDialogComponent>) { 
    this.paymentDialog.disableClose = true;
  }

  ngOnInit(): void {
  }

  onClose () {
    this.paymentDialog.close();
  }

}
