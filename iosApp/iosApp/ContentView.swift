import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject var sessionViewModel = SessionViewModel.shared

	var body: some View {
        if sessionViewModel.isLoggedIn || sessionViewModel.showHome {
            HomeView()
        } else {
            LoginView()
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
