package kr.inhatc.spring.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.member.dto.ImageDto;
import kr.inhatc.spring.user.entity.Images;
import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.repository.ImageRepository;
import kr.inhatc.spring.user.repository.UserRepository;
import kr.inhatc.spring.utils.FileUtils;
import kr.inhatc.spring.utils.ThumnailUtils;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	private FileUtils fileUtils;

	@Override
	public List<Users> userList() {
		List<Users> list =userRepository.findAllByOrderByIdDesc();
		//System.out.println("================> 크기 : " + list.size());
		//System.out.println(list.get(0));
		return list;
	}

	@Override
	public void saveUsers(Users user) {
		userRepository.save(user);
	}


	@Override
	public void saveUsersWithFile(Users user, MultipartHttpServletRequest multipartHttpServletRequest) {
		userRepository.save(user);
		// 1. 파일 저장
		List<Images> list = fileUtils.parseFileInfoUser(user.getId(), multipartHttpServletRequest);

		// 2. 디비 저장
		if(CollectionUtils.isEmpty(list) == false) {
			imageRepository.saveAll(list);
		}
	}

	@Override
	public Users userDetail(String id) {
		Optional<Users> optional = userRepository.findById(id);
		if(optional.isPresent()) {
			Users user = optional.get();
			ThumnailUtils thumnail = new ThumnailUtils();
			//파일 정보
			Images image_user = imageRepository.findByUserId(id);
			String image;
			if(image_user == null) {
				image = "src/main/resources/static/images-member/user.png";
			} else {
				image = image_user.getStoredFilePath();
			}
			thumnail.makeThumnail(image);
			String img = image.replaceFirst("src/main/resources/static", "");
			user.setM_image(img);

			return user;
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public void userDelete(String id) {
		userRepository.deleteById(id);
	}



}
