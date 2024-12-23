//const likeButton = document.getElementById('like-button');
//
//likeButton.addEventListener('click', () => {
//    const heart = likeButton.querySelector('.heart');
//
//    if(likeButton.classList.contains('liked')){
//        heart.innerHTML = '&#9825;';
//        likeButton.classList.remove('liked');
//    }
//    else{
//        heart.innerHTML = '&#9829;';
//        likeButton.classList.add('liked');
//    }
//});


const showComments = (button) => {
    const parentDiv = button.closest('.other-comment');

    const helpInput = parentDiv.querySelector('[name="help"]');
    const className = '.' + helpInput.value;

    const modal = document.querySelectorAll(className);

    modal.forEach((reply) => {
        if (reply.classList.contains('hidden')) {
            reply.classList.remove('hidden');
        } else {
            reply.classList.add('hidden');
        }
    });

}


const showReply = (button) => {
    const superDiv = button.closest('.comment-section');
    const parentDiv = button.closest('.other-comment');
    const helpInput = parentDiv.querySelector('[name="reply"]');
    const className = '.' + helpInput.value;
    const modal = document.querySelector(className);


    if (modal.classList.contains('hidden')) {
        modal.classList.remove('hidden');
    } else {
        modal.classList.add('hidden');
    }

    const allReplies = superDiv.querySelectorAll('.reply');
    allReplies.forEach((reply) => {
        if (reply !== modal && !reply.classList.contains('hidden')) {
            reply.classList.add('hidden');
        }
    });
};




const scrollToComment = (button) => {
    const commentSection = document.getElementById('myComment');
    if (commentSection) {
        commentSection.scrollIntoView({ behavior: 'smooth', block: 'center' });
    }
};