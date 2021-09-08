import { Role } from './role';
import { Base } from '../base/base';
import { Module } from './module';
export class User extends Base {
  lastName: string;
  email?: string;
  password?: string;
  role: Role;
  documentNumber?: string;
  guid?: string;
  modules: Module[];
  token?: string;
}
