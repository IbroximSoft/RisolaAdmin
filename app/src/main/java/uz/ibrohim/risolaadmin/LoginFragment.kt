package uz.ibrohim.risolaadmin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import uz.ibrohim.risolaadmin.databinding.FragmentLoginBinding
import uz.ibrohim.risolaadmin.utils.Preferences
import uz.ibrohim.risolaadmin.utils.errorToast
import uz.ibrohim.risolaadmin.utils.progressInterface
import uz.ibrohim.risolaadmin.utils.warningToast

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        //Account -> risola5530@gmail.com
        auth = FirebaseAuth.getInstance()

        Preferences.init(requireContext())
        val uid: String = Preferences.uid
        if (uid.isNotEmpty()) {
            findNavController().navigate(R.id.homeFragment)
        }

        progressInterface(binding.circularProgress)

        binding.loginBtn.setOnClickListener {
            userLogin()
        }

        return binding.root
    }

    private fun userLogin() {
        binding.apply {
            val login = loginNumber.text.toString()
            val password = loginPassword.text.toString()

            if (login.isEmpty() || password.isEmpty()) {
                warningToast("Barchasini to'ldiring")
            } else {
                loginBtn.isVisible = false
                loginProgress.isVisible = true
                auth.signInWithEmailAndPassword("$login@risola.uz", password)
                    .addOnCompleteListener(requireActivity()) {
                        loginBtn.isVisible = false
                        loginProgress.isVisible = true
                        if (it.isSuccessful) {
                            val currentUserId = auth.currentUser!!.uid
                            Preferences.uid = currentUserId
                            findNavController().navigate(R.id.homeFragment)
                        } else {
                            errorToast("Xatolik yuz berdi!")
                        }
                    }
            }
        }
    }
}