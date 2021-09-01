import { Role } from './role';
export class User {
  name: string;
  lastName: string;
  email: string;
  password: string;
  role: Role;
  token?: string;
}
