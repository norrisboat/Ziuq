//
//  LoginView.swift
//  iosApp
//
//  Created by Norris Aboagye on 01/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import KMMViewModelSwiftUI

struct LoginView: View {
    
    @ObservedObject var sessionViewModel: SessionViewModel = .shared
    
    @State private var username: String = ""
    @State private var password: String = ""
    @ObservedViewModel var viewModel = LoginViewModel()
    @State private var path: [LoginNavPath] = []
    
    var body: some View {
        NavigationStack(path: $path) {
            ZStack(alignment: .center) {
                switch getState(screenState: viewModel.state) {
                case .idle:
                    VStack {
                        HStack {
                            Spacer()
                            ZiuqText(text: Labels().noAccountRegister.localized(), type: .subTitle, color: .deepGreen)
                                .makeButton {
                                    path.append(.register)
                                }
                        }
                            .padding(.top, 36)
                            .padding(.trailing)
                        Spacer()
                        VStack(alignment: .leading) {
                            ZiuqText(text: Labels().app.localized(), type: .heading, color: .deepGreen)
                            ZiuqText(text: Labels().tagline.localized(), type: .title, color: .deepGreen)
                            
                            ZiuqTextField(text: $username, placeholder: Labels().username.localized())
                                .padding(.top, 16.0)
                            ZiuqTextField(text: $password, placeholder: Labels().password.localized(), isSecure: true)
                            
                            PrimaryButton(title: Labels().login.localized(), isDisabled: username.isEmpty || password.isEmpty) {
                                viewModel.login(username: username, password: password)
                            }
                        }
                        Spacer()
                    }.fillMaxSize()
                case .loading:
                    ProgressView()
                case .success:
                    EmptyView()
                case .error(let errorMessage):
                    ZiuqText(text: errorMessage, type: .label, color: .deepGreen)
                    
                }
            }
            .padding()
            .fillMaxSize(alignment: .center)
            .background(Color.primaryGreen)
            .ignoresSafeArea()
            .navigationDestination(for: LoginNavPath.self) { path in
                switch path {
                case .register:
                    RegisterView()
                }
            }
        }
        .tint(.label)
    }
}

enum LoginNavPath: Hashable {
    case register
}

enum LoginViewState {
    case idle
    case loading
    case success
    case error(String)
}

extension LoginView {
    
    func getState(screenState: LoginScreenState) -> LoginViewState {
        if screenState as? LoginScreenStateIdle != nil {
            return .idle
        } else if screenState as? LoginScreenStateLoading != nil {
            return .loading
        } else if screenState as? LoginScreenStateSuccess != nil {
            sessionViewModel.showHome(true)
            return .success
        } else if screenState as? LoginScreenStateError != nil {
            return .error((screenState as? LoginScreenStateError)?.errorMessage ?? "Something went wrong")
        }
        
        return .idle
    }
    
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
