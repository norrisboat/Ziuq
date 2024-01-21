//
//  RegisterView.swift
//  iosApp
//
//  Created by Norris Aboagye on 16/01/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared
import KMMViewModelSwiftUI

struct RegisterView: View {
    
    @ObservedObject var sessionViewModel: SessionViewModel = .shared
    
    @State private var username: String = ""
    @State private var password: String = ""
    @ObservedViewModel var viewModel = RegisterViewModel()
    
    var body: some View {
        ZStack(alignment: .center) {
            
            switch getState(screenState: viewModel.state) {
            case .idle:
                VStack(alignment: .leading) {
                    ZiuqText(text: Labels().register_.localized(), type: .heading, color: .deepGreen)
                    ZiuqText(text: Labels().tagline.localized(), type: .title, color: .deepGreen)
                    
                    ZiuqTextField(text: $username, placeholder: Labels().username.localized())
                        .padding(.top, 16.0)
                    ZiuqTextField(text: $password, placeholder: Labels().password.localized(), isSecure: true)
                    
                    PrimaryButton(title: Labels().register_.localized(), isDisabled: username.isEmpty || password.isEmpty) {
                        viewModel.register(username: username, password: password)
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

enum RegisterViewState {
    case idle
    case loading
    case success
    case error(String)
}

extension RegisterView {
    
    func getState(screenState: RegisterScreenState) -> RegisterViewState {
        if screenState as? RegisterScreenStateIdle != nil {
            return .idle
        } else if screenState as? RegisterScreenStateLoading != nil {
            return .loading
        } else if screenState as? RegisterScreenStateSuccess != nil {
            sessionViewModel.showHome(true)
            return .success
        } else if screenState as? RegisterScreenStateError != nil {
            return .error((screenState as? RegisterScreenStateError)?.errorMessage ?? "Something went wrong")
        }
        
        return .idle
    }
    
}

#Preview {
    RegisterView()
}
