import {User} from "./User";
import {Address} from "./Address";

export interface RideOffer {
  id: number;
  title: string;
  description: string;
  startTime: Date;
  user: User;
  fromAddress: Address;
  toAddress: Address;
}
