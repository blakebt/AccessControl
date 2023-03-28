import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;

class AccessControl {
  // Declare the hash map that stores the users and a LinkedList of their files
  private HashMap<String, LinkedList<FileNode>> accessList;

  // Methood to create the hashmap
  public AccessControl() {
    this.accessList = new HashMap<>();
  }

  // Method to load the acm entries
  public void loadEntries(String user, String fileName, String permission) {
    // Check if the user is already in the accessList
    if (!checkUser(user)) {
      // Create new user and their list
      this.addUser(user);
    }
    // See if the file is already in the user's list
    if (!checkFile(user, fileName)) {
      // Create new file
      this.addFile(user, fileName);
    }
    // Adding the permissions to the file after ensuring the user and files exist
    this.addPermission(user, fileName, permission);
  }

  // Method to load the acm entries
  public void addEntry(String user, String fileName, String permission) {
    // Check if the user is already in the accessList
    if (!checkUser(user)) {
      // Create new user and their list
      this.addUser(user);
    }
    // See if the file is already in the user's list
    if (!checkFile(user, fileName)) {
      // Create new file
      this.addFile(user, fileName);
    }
    // Adding the permissions to the file after ensuring the user and files exist
    if(this.addPermission(user, fileName, permission)){
       System.out.println("Succesfully Updated");
    }
   
  }

  // Method to remove entry
  public void removeEntry(String user, String fileName, String permission) {
    // Check that the user and file e
    if (!checkUser(user) || !checkFile(user, fileName)) {
      System.out.println("Invalid Update. Entry not found.");
    } else {
      // Removing the permissions to the file after ensuring the user and file exist
      LinkedList<FileNode> userList = this.accessList.get(user);
      for (int i = 0; i < userList.size(); i++) {
        // Remove perission from file
        if (userList.get(i).getName().equals(fileName)) {
          userList.get(i).removePermission(permission);
          System.out.println("Successfully Updated");

          // Remove file if it has no permissions
          if (userList.get(i).getPermissions().isEmpty()) {
            removeFile(user, fileName);
          }
          // Remove user if they have no files
          if (userList.isEmpty()) {
            removeUser(user);
          }

          break;
        }
      }
    }
  }

  // Method to check the request
  public boolean evaluateEntry(String user, String fileName, String permission) {
    // will use our check methods for each part of the request
    return this.checkUser(user)
        && this.checkFile(user, fileName)
        && this.checkPermission(user, fileName, permission);
  }

  // Method to print the ACM
  public void printACM() {
    // Looping through each users, their lists, and their permissions.
    for (String user : this.accessList.keySet()) {
      System.out.println("\n"+ user);
      LinkedList<FileNode> userList = this.accessList.get(user);
      for (FileNode fileNode : userList) {
        System.out.print(fileNode.getName() + ": [ ");
        ArrayList<String> permissions = fileNode.getPermissions();
        for (String filePermission : permissions) {
          System.out.print(filePermission + " ");
        }
        System.out.println("]");
      }
    }
  }

  // Method to check is an user is in the ACM
  private boolean checkUser(String user) {
    return this.accessList.containsKey(user);
  }

  // Method to check if a user has a certain file
  private boolean checkFile(String user, String file) {
    LinkedList<FileNode> userList = this.accessList.get(user);
    for (FileNode fileNode : userList) {
      if (fileNode.getName().equals(file)) {
        return true;
      }
    }
    return false;
  }

  // Method to check if a user's file has a certain permission
  private boolean checkPermission(String user, String file, String permission) {
    LinkedList<FileNode> userList = this.accessList.get(user);

    for (int i = 0; i < userList.size(); i++) {
      if (userList.get(i).getName().equals(file)) {
        return userList.get(i).hasPermission(permission);
      }
    }
    return false;
  }

  // Method to add a pemrission to a user's file
  private boolean addPermission(String user, String file, String permission) {
    LinkedList<FileNode> userList = this.accessList.get(user);
    for (FileNode fileNode : userList) {
      if (fileNode.getName().equals(file)) {
        if(fileNode.addPermission(permission)){
          return true;
        }
      }
    }
    return false;
  }

  // Method to remove a permission from a user's file
  private void removePermission(String user, String file, String permission) {
    LinkedList<FileNode> userList = this.accessList.get(user);
    for (FileNode fileNode : userList) {
      if (fileNode.getName().equals(file)) {
        fileNode.removePermission(permission);
        break;
      }
    }
  }

  // Method to add user
  private void addUser(String user) {
    this.accessList.put(user, new LinkedList<>());
    System.out.println("User added.");
  }

  // Method to remove user
  private void removeUser(String user) {
    this.accessList.remove(user);
    System.out.println("User removed.");
  }

  // Method to add file
  private void addFile(String user, String file) {
    FileNode newFile = new FileNode(file);
    this.accessList.get(user).offer(newFile);
  }

  // Method to remove file
  private void removeFile(String user, String file) {
    LinkedList<FileNode> userList = this.accessList.get(user);
    for (FileNode fileNode : userList) {
      if (fileNode.getName().equals(file)) {
        userList.remove(fileNode);
        break;
      }
    }
  }
}