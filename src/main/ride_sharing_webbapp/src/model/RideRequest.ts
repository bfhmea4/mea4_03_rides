import {User} from "./User";
import {Address} from "./Address";

export interface RideRequest {
  id: number;
  title: string;
  description: string;
  user: User;
  fromAddress: Address;
  toAddress: Address;
}
