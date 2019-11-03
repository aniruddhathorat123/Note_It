package com.aniruddha.writerapp



/**
 * A simple [Fragment] subclass.
 */
/*
class SignUpVerifyFragment : Fragment(), View.OnClickListener {

    private lateinit var email: String
    private val firebase = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       return inflater.inflate(R.layout.fragment_sign_up_verify, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        email = DatabaseHandler.getInstance(requireContext()).getUserEmail()
        if (email != "") {
            emailEditText.setText(email)
            password.visibility = View.VISIBLE
            confirmPassoword.visibility = View.VISIBLE
            changeEmail.visibility = View.INVISIBLE
            verifyAndSubmitBt.setText(R.string.create_user_text)
        }
        verifyAndSubmitBt.setOnClickListener(this)
        cancelVerifyAndSubmit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.verifyAndSubmitBt -> {
                if (password.text.toString() == confirmPassoword.text.toString()
                    && android.util.Patterns.EMAIL_ADDRESS
                        .matcher(emailEditText.text.toString()).matches()) {
                    changeProgressBarState(1)
                    verifyUserEmailAndSubmit(emailEditText.text.toString(),
                        password.text.toString())
                } else {
                    firebaseError(2)
                }
            }
        }
    }

    private fun  verifyUserEmailAndSubmit(email: String, pass: String) {
        firebase.createUserWithEmailAndPassword(email,pass)
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    firebase.currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener { verify ->
                            if (verify.isSuccessful) {
                                changeProgressBarState(0)
                                DatabaseHandler.getInstance(requireContext())
                                    .setUserEmailAndPass(email,pass)
                                DialogBuilders(requireContext())
                                    .emailVerificationSendBuilder()
                                    .show()
                            } else {
                                firebaseError(1)
                            }
                        }
                } else {
                    firebaseError(0)
                }
            }
        changeProgressBarState(0)
    }

    private fun firebaseError(code: Int) {
        when (code) {
            0 -> Toast.makeText(requireContext(),R.string.wrong_email_msg,Toast.LENGTH_LONG)
                .show()
            1 -> Toast.makeText(requireContext(),R.string.email_error_msg,Toast.LENGTH_LONG)
                .show()
            2 -> Toast.makeText(requireContext(),R.string.wrong_email_and_pass_msg,Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun changeProgressBarState(state: Int) {
        when (state) {
            0 -> emailVerifyProgressBar.visibility = View.GONE
            1 -> emailVerifyProgressBar.visibility = View.VISIBLE
        }
    }
}
*/
