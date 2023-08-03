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
    
    var body: some View {
        ZStack(alignment: .center) {
            
            switch getState(screenState: viewModel.state) {
            case .idle:
                VStack(alignment: .leading) {
                    ZiuqText(text: Labels().app.localized(), type: .heading, color: .deepGreen)
                    ZiuqText(text: Labels().tagline.localized(), type: .title, color: .deepGreen)
                    
                    ZiuqTextField(text: $username, placeholder: Labels().username.localized())
                        .padding(.top, 16.0)
                    ZiuqTextField(text: $password, placeholder: Labels().password.localized(), isSecure: true)
                    
                    PrimaryButton(title: Labels().login.localized(), isDisabled: username.isEmpty && password.isEmpty) {
                        viewModel.login(username: username, password: password)
                    }
                }
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
    }
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
