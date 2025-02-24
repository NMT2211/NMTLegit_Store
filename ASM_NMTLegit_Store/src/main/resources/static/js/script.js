
    const avatarsNam = [
        "https://hocdohoacaptoc.com/wp-content/uploads/2022/02/avata-dep-nam-1.jpg.webp",
        "https://hocdohoacaptoc.com/wp-content/uploads/2022/02/avata-dep-nam-2.jpg.webp",
        "https://hocdohoacaptoc.com/wp-content/uploads/2022/02/avata-dep-nam-3.jpg.webp",
        "https://hocdohoacaptoc.com/wp-content/uploads/2022/02/avata-dep-nam-4.jpg.webp",
        "https://hocdohoacaptoc.com/wp-content/uploads/2022/02/avata-dep-nam-6.jpg.webp"
    ];

    const avatarsNu = [
        "https://hocdohoacaptoc.com/wp-content/uploads/2022/02/avata-dep-nu-18.jpg.webp",
        "https://hocdohoacaptoc.com/wp-content/uploads/2022/02/avata-dep-nu-13.jpg.webp",
        "https://hocdohoacaptoc.com/wp-content/uploads/2022/02/avata-dep-nu-4.jpg.webp",
        "https://hocdohoacaptoc.com/wp-content/uploads/2022/02/avata-dep-nu-19.jpg.webp",
        "https://hocdohoacaptoc.com/wp-content/uploads/2022/02/avata-dep-nu-11.jpg.webp"
    ];

    const gioiTinh = "[[${session.gt != null ? session.gt : false}]]" === "true";


    // Kiểm tra avatar đã lưu trong localStorage chưa
    let avatar = localStorage.getItem("userAvatar");

    if (!avatar) {
        // Nếu chưa có avatar, chọn ảnh ngẫu nhiên rồi lưu vào localStorage
        avatar = gioiTinh 
            ? avatarsNam[Math.floor(Math.random() * avatarsNam.length)] 
            : avatarsNu[Math.floor(Math.random() * avatarsNu.length)];

        localStorage.setItem("userAvatar", avatar);
    }

    // Cập nhật ảnh avatar trên giao diện khi trang tải xong
    document.addEventListener("DOMContentLoaded", function () {
        const avatarImg = document.getElementById('avatarImg');
        if (avatarImg) {
            avatarImg.src = avatar;
            avatarImg.alt = "Avatar người dùng";
        }
    });

    
    
    
    
    
    