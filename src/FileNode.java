import java.util.ArrayList;

/*
This implementation should work if we use the built-in Java LinkedList class.
See AccessControl.java for current implementation using the LinkedList class.
If we don't use the built-in class, we just need to add a next pointer to this class.
*/
class FileNode {
  private String name;
  private ArrayList<String> permissions;

  // Constuctor if we already know the permissions when creating the new node
  // This may be the case, but I'm not sure yet.
  public FileNode(String name, ArrayList<String> permissions) {
    this.name = name;
    this.permissions = permissions;
  }

  // Constructor if we don't already know the permissions when creating the new
  // node
  public FileNode(String name) {
    this.name = name;
    this.permissions = new ArrayList<>();
  }

  // method to get the file name
  // we don't need a set method, because the name will never change
  // after instantiation
  public String getName() {
    return this.name;
  }

  // Method to get the ArrayList of permissions for the file
  public ArrayList<String> getPermissions() {
    return this.permissions;
  }

  // method to check if a permission exists in the node's permissions ArrayList
  // just uses the built-in ArrayList method contains to check
  public boolean hasPermission(String permission) {
    return this.permissions.contains(permission);
  }

  // method to add permission
  // only adds if the user doesn't have the permission
  public boolean addPermission(String permission) {
    if (!this.permissions.contains(permission)) {
      this.permissions.add(permission);
      return true;
    } else {
      System.out.println("User already has this permission");
      return false;
    }
  }

  // method to remove permission
  // prints error message if the user doesn't have the permission
  public void removePermission(String permission) {
    if (!this.permissions.remove(permission)) {
      System.out.println("User does not have this permission");
    } else {
      System.out.println(permission + " permission removed");
    }
  }
}