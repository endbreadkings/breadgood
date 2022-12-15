import 'package:get/get.dart';
import 'package:breadgood_app/modules/my_page/model/user_info.dart';

class MyPageController extends GetxController {
  User user;

  updateUser(User updatedUser) {
    user = updatedUser;
    update();
  }
}
