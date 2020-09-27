import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { MutualFundsService } from '../../Services/mutual-funds.service';

@Component({
  selector: 'app-purchase-funds-details',
  templateUrl: './purchase-funds-details.component.html',
  styleUrls: ['./purchase-funds-details.component.css']
})
export class PurchaseFundsDetailsComponent implements OnInit {

  purchase_data: any = [];

  constructor(private router: Router, private route: ActivatedRoute, private mutualFundsService: MutualFundsService) { }

  ngOnInit(): void {
    this.Fundsdetails()
  }

  purhasefunds() {
    this.router.navigate(["/Purchase funds"])
  }

  Fundsdetails() {
    const Id = this.route.snapshot.params.Id
    this.mutualFundsService.getpurchasedFundsdetail(Id).subscribe(
      fund_data => {
        this.purchase_data = fund_data.orderFunds;
        console.log( this.purchase_data);
      },
      error => {
        console.log(error)
      }
    )
  }


  DeleteFunds(Id) {
    
    this.mutualFundsService.getMutualFundsDelete(Id).subscribe(
      fund_data => {
        
        this.router.navigate(["/Login"])
      },
      error => {
        console.log(error)
      }
    )
  }
  

}
