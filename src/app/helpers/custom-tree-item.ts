import { TreeviewItem, TreeItem } from 'ngx-treeview';
import { isNil } from 'lodash';
export interface TreeModel extends TreeItem {
  legends: any;
}
export class CustomTreeItem extends TreeviewItem {
  legends: any;
  constructor(exItem: TreeModel) {
    super((exItem as unknown) as TreeItem);
    if (exItem.legends) {
      this.legends = exItem.legends;
    }
    if (!isNil(exItem.children) && exItem.children.length > 0) {
      this.children = exItem.children.map((child) => {
        if (this.disabled === true) {
          child.disabled = true;
        }

        return this.getModel(child);
      });
    }
  }

  getModel(child) {
    return CustomTreeItem ? new CustomTreeItem(child) : new TreeviewItem(child);
  }
}
